package com.xecoder.common.exception;


import org.springframework.http.HttpStatus;

/**
 * Created by yanglu
 */
public class HabitException extends Exception {

        private static final long serialVersionUID = 3384172151996020457L;

        private HttpStatus status;

	public HabitException(HttpStatus status) {
            setStatus(status);
        }

	public HabitException(HttpStatus status, String message) {
            super(message);
            setStatus(status);
        }

	public HabitException(HttpStatus status, String message, Throwable exc) {
            super(message, exc);
            setStatus(status);
        }


        /**
         * @return the status
         */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    private void setStatus(HttpStatus status) {
        this.status = status;
    }
}

