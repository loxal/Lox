/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.shared;

/**
 * Wrapper exception that gets thrown when Objectify get() returns too many results
 */
public class TooManyResultsException extends Exception {

    public TooManyResultsException() {
        super();
    }

    public TooManyResultsException(Throwable t) {
        super(t);
    }

    public TooManyResultsException(String msg) {
        super(msg);
    }

    public TooManyResultsException(String msg, Throwable t) {
        super(msg, t);
    }

}