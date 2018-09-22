package com.es.phoneshop.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.es.phoneshop.model.helping.Constants.ENCODING;

public class DosFilter implements Filter {
    private final int MAX_REQUEST_COUNT = 10;
    private final int INTERVAL_IN_MILLISECONDS = 5 * 1000;
    private final Integer WAIT_INTERVAL_IN_MILLISECONDS = 10 * 1000;
    private final int MILLISECONDS_IN_SECOND = 1000;
    private final Integer TOO_MANY_REQUESTS_ERROR = 429;
    private boolean accessAllowed = true;
    private Timer timer, errorTimer;
    private TimerTask task, errorTask;
    private String address;
    private String WAIT_ATTRIBUTE_NAME = "wait";

    Map requestCountMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        update();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        servletRequest.setCharacterEncoding(ENCODING);
        servletRequest.setAttribute(WAIT_ATTRIBUTE_NAME,WAIT_INTERVAL_IN_MILLISECONDS / MILLISECONDS_IN_SECOND);

        if (!accessAllowed) {
            destroyTimer(errorTimer,errorTask);
            updateErrorTimer(servletResponse);
            ((HttpServletResponse) servletResponse).sendError(TOO_MANY_REQUESTS_ERROR);
        } else {
            address = servletRequest.getRemoteAddr();
            Integer count = (Integer) requestCountMap.get(address);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }

            requestCountMap.put(address, count);

            if (count > MAX_REQUEST_COUNT) {

                accessAllowed = false;

                updateErrorTimer(servletResponse);

                destroyTimer(timer,task);

                ((HttpServletResponse) servletResponse).sendError(TOO_MANY_REQUESTS_ERROR);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

        }
    }

    @Override
    public void destroy() {

    }

    private void update() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (address != null) {
                    requestCountMap.remove(address);
                }
            }
        };
        timer.scheduleAtFixedRate(task, INTERVAL_IN_MILLISECONDS, INTERVAL_IN_MILLISECONDS);
    }

    private void updateErrorTimer(ServletResponse response) {
        errorTimer = new Timer();
        errorTask = new TimerTask() {
            @Override
            public void run() {
                accessAllowed = true;
                if (address != null) {
                    requestCountMap.remove(address);
                }
                destroyTimer(errorTimer,errorTask);
                update();
            }
        };
        errorTimer.scheduleAtFixedRate(errorTask, WAIT_INTERVAL_IN_MILLISECONDS, WAIT_INTERVAL_IN_MILLISECONDS);
    }

    private void destroyTimer(Timer timer,TimerTask timerTask) {
        timer.cancel();
        timerTask.cancel();
        timer = null;
        timerTask = null;
    }
}