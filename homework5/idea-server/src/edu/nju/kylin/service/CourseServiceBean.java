package edu.nju.kylin.service;

import edu.nju.kylin.dao.CourseDao;
import edu.nju.kylin.model.Course;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by kylin on 19/12/2016.
 * All rights reserved.
 */
@Stateless
public class CourseServiceBean implements CourseService {

    @EJB
    private CourseDao dao;

//    public CourseServiceBean() {
//        this.dao = (CourseDao) EJBFactory
//                .getEJB("ejb:/onlineStockJPAEJB/CourseDaoBean!edu.nju.onlinestock.dao.CourseDao");
//    }

    @Override
    public Course getCourse(int id) {
        return this.dao.getCourse(id);
    }

}
