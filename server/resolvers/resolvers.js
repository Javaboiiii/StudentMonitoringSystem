import { where } from "sequelize";
import bcrypt from "bcrypt";
import { Op } from '@sequelize/core';
import Assignmet from "../models/assignment.js";
import Course from "../models/course.js";
import Enrollment from "../models/enrollment.js";
import User from "../models/user.js";

const resolvers = {
    Course: {
        user: async (parent, args) => { 
            const user = await User.findByPk(parent.user_id)
            return user
        }
    },
    Enrollment: {
        courses: async (parent, args) => {
            return await Course.findByPk(parent.course_id)
        },
        users: async (parent, args) => {
            return await User.findByPk(parent.user_id)
        }
    },

    Assignment: {
        course: async (parent, args) => {
            return await Course.findByPk(parent.course_id)
        }
    },

    Query: {
        users: async (parent, args) => {
            const users = await User.findAll()
            return users
        },
        courses: async (parent, args) => { 
            const courses = await Course.findAll({ 
                include: { 
                    all: true , 
                    nested: true 
                }
            }) 
            return courses
        }, 
        user: async (parent, { user_id }) => {
            const user = await User.findOne({ user_id: user_id })
            console.log(user)
            return user
        },

        assignment: async () => {
            return await Assignmet.findAll()
        },

        enrollments: async () => {
            return await Enrollment.findAll()
        }
    },
    Mutation: {
        createUser: async (parent, { user }) => {
            try {
                const findUser = await User.findOne({where:{
                    [Op.or]:[
                    {
                        username: user.username
                    },
                    {
                        email: user.email
                    }]
                }})
                if(findUser){ 
                    throw new Error('Already resgistered user')
                }else {
                    const password = await bcrypt.hash(user.password, 8)
                    const newUser = await User.create({
                        email: user.email,
                        password: password,
                        role: user.role,
                        username: user.username
                    });
                    return newUser;
                }
            } catch (error) {
                console.error("Error creating user:", error);
                throw error;
            }
        },
        createCourse: async (parent, { course }) => {
            try {
                const findCourse = await Course.findOne({
                    where: {
                        title: course.title
                    }
                })
                if(findCourse) { 
                    throw new Error('Course name already Taken') ;
                }
                const findUser = await User.findOne({where:{user_id: course.user_id}})
                if(!findUser){ 
                    throw new Error('User do not Exist')
                }
                const newCourse = await Course.create({
                    description: course.description,
                    end_date: course.end_date,
                    start_date: course.start_date,
                    title: course.title,
                    user_id : course.user_id
                });

                return newCourse;
            } catch (error) {
                console.error("Error creating user:", error);
                throw error;
            }
        },
        createAssignment: async (parent, { assignment }) => {
            try {
                const newUser = await Assignmet.create({
                    description: assignment.description,
                    due_date: assignment.due_date,
                    title: assignment.title,
                    course_id: assignment.course_id
                });
                console.log(newUser);
                return newUser;
            } catch (error) {
                console.error("Error creating user:", error);
                throw error;
            }
        },
        checkPassword: async (parent, args) => { 
            const findUser = await User.findOne({where: {username: args.username}})
            if(!findUser) { 
                throw new Error("Something is wrong")
            }
            const authenticate = await bcrypt.compare(args.password, findUser.password)
            if(authenticate) { 
                console.log("Password is Right");
                return findUser
            }else { 
                throw new Error("Something is Wrong")
            }
            
        }
    }
}

export default resolvers; 