package br.com.code.plus.project.exceptions;

public class DomainBusinessException extends RuntimeException {

    private static final long serialVersionUID = -1038516725736340750L;

    public DomainBusinessException(final String msg) {
        super(msg);
    }

}
