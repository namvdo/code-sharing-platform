package entity;


import util.Util;

import java.time.LocalDateTime;

/**
 * @author namvdo
 */

public class CodeResponse {

    private String code;
    private LocalDateTime date;
    private int hashCode;

    public CodeResponse() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CodeResponse that = (CodeResponse) o;
        return code.equals(that.code) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        // identical to Objects.hash(), but has a better performance, Objects.hash
        // needs to create an array with a variable number of arguments,
        // boxing and unboxing can be happened all the time.
        int result = hashCode;
        if (result == 0) {
            result = date.hashCode();
            result = 31 * result + code.hashCode();
            hashCode = result;
        }
        return result;
    }

    private CodeResponse(CodeBuilder builder) {
        this.code = builder.codeFragment;
        this.date = builder.dateTime;
    }



    public static class CodeBuilder {
        private final String codeFragment;
        private LocalDateTime dateTime;

        public CodeBuilder(String codeFragment) {
            this.codeFragment = codeFragment;
        }

        public CodeBuilder dateTime(LocalDateTime date) {
            this.dateTime = date;
            return this;
        }

        public CodeResponse build() {
            return new CodeResponse(this);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CodeResponse{" +
                "code='" + code + '\'' +
                ", date=" + date +
                '}';
    }

    public String getDate() {
        if (this.date == null) {
            return null;
        }
        return Util.formatDateTime(date);
    }
}
