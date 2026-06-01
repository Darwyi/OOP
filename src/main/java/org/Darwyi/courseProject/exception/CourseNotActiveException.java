package org.Darwyi.courseProject.exception;

public class CourseNotActiveException extends LearningPlatformException {
    private static final long serialVersionUID = 1L;
    public CourseNotActiveException(String message){ super(message); }
}
