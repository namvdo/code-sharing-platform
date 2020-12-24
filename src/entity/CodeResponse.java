package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import util.Util;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author namvdo
 */

@Entity
public class CodeResponse {
    @Id
    @JsonIgnore
    private int id;
    private String code;
    private LocalDateTime date;
    @Transient
    private int time;
    @Transient
    private int views;
    @Transient
    private int hashCode;
    @Transient
    @JsonIgnore
    private boolean isCodePublic;
    @JsonIgnore
    @Transient
    private long createdTime;
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
        this.id = builder.id;
        this.views = builder.view;
        this.time = builder.timeInSecond;
        this.isCodePublic = builder.isCodePublic;
        this.createdTime = builder.createdTime;
    }


    public static class CodeBuilder {
        private final String codeFragment;
        private LocalDateTime dateTime;
        private int id;
        private int timeInSecond;
        private int view;
        private boolean isCodePublic;
        private long createdTime;
        public CodeBuilder(String codeFragment) {
            this.codeFragment = codeFragment;
        }

        public CodeBuilder dateTime(LocalDateTime date) {
            this.dateTime = date;
            return this;
        }

        public CodeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CodeBuilder timeInSecond(int time) {
            this.timeInSecond = time;
            return this;
        }

        public CodeBuilder view(int view) {
            this.view = view;
            return this;
        }
        public CodeBuilder codePublic(boolean isPublic) {
            this.isCodePublic = isPublic;
            return this;
        }
        public CodeBuilder createdTime(long time) {
            this.createdTime = time;
            return this;
        }
        public CodeResponse build() {
            return new CodeResponse(this);
        }
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CodeResponse{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", views=" + views +
                ", hashCode=" + hashCode +
                ", isCodePublic=" + isCodePublic +
                ", createdTime=" + createdTime +
                '}';
    }

    public String getDate() {
        if (this.date == null) {
            return null;
        }
        return Util.formatDateTime(date);
    }

    public int getTime() {
        return time;
    }

    @JsonIgnore
    public long getCreatedTime() {
        return createdTime;
    }
    public int getViews() {
        return views;
    }
    public boolean isCodePublic() {
        return isCodePublic;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getId() {
        return this.id;
    }
}
