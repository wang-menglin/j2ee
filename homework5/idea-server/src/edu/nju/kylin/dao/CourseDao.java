package edu.nju.kylin.dao;

import edu.nju.kylin.model.Course;

import javax.ejb.Remote;
import java.io.Serializable;

/**
 * Created by kylin on 19/12/2016.
 * All rights reserved.
 */
@Remote
public interface CourseDao extends Serializable{

    Course getCourse(int id);

}
