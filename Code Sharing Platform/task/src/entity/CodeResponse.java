package entity;


import java.time.LocalDateTime;

/**
 * @author namvdo
 */

public class CodeResponse {

    private String code;
    private LocalDateTime date;
    public CodeResponse() {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
