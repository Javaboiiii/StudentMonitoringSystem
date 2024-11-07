import { Sequelize } from "sequelize";
import bcrypt from "bcrypt";
import { Op } from '@sequelize/core';
import Assignment from "../models/assignment.js";
import Course from "../models/course.js";
import Enrollment from "../models/enrollment.js";
import User from "../models/user.js";
import User_Assignments from "../models/user_assignments.js";
import sendMail from "../mailer/nodeMailer.js";

const sequelize = new Sequelize('postgres://devraj:123@localhost:5432/studentmonitoring');

const resolvers = {
    Course: {
        user: async (parent, args) => {
            const user = await User.findByPk(parent.user_id)
            return user
        }
    },
    Enrollment: {
        course: async (parent, args) => {
            return await Course.findByPk(parent.course_id)
        },
        user: async (parent, args) => {
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
                    all: true,
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
            return await Assignment.findAll()
        },

        enrollments: async () => {
            return await Enrollment.findAll()
        },

        user_assignments: async () => {
            return await User_Assignments.findAll();
        }
    },
    Mutation: {
        createUser: async (parent, { user }) => {
            try {
                const findUser = await User.findOne({
                    where: {
                        [Op.or]: [
                            {
                                username: user.username
                            },
                            {
                                email: user.email
                            }]
                    }
                })
                if (findUser) {
                    throw new Error('Already resgistered user')
                } else {
                    const password = await bcrypt.hash(user.password, 8)
                    const newUser = await User.create({
                        email: user.email,
                        password: password,
                        role: user.role,
                        prn: user.prn,
                        username: user.username,
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
                if (findCourse) {
                    throw new Error('Course name already Taken');
                }
                const findUser = await User.findOne({ where: { user_id: course.user_id } })
                if (!findUser) {
                    throw new Error('User do not Exist')
                }
                if (findUser.role === "student") {
                    throw new Error("Student cannot create Course");
                }
                const newCourse = await Course.create({
                    description: course.description,
                    end_date: course.end_date,
                    start_date: course.start_date,
                    title: course.title,
                    user_id: course.user_id
                });

                return newCourse;
            } catch (error) {
                console.error("Error creating user:", error);
                throw error;
            }
        },

        createAssignment: async (parent, { assignment }) => {
            const findAssignment = await Assignment.findOne({
                where: {
                    [Op.and]: [
                        { title: assignment.title },
                        { course_id: assignment.course_id }
                    ]
                }
            })
            if (findAssignment) {
                throw new Error("Assignment already exists in the Course")
            }
            const t = await sequelize.transaction();
            try {
                const newAssignment = await Assignment.create({
                    description: assignment.description,
                    due_date: assignment.due_date,
                    title: assignment.title,
                    course_id: assignment.course_id,
                    end_time: assignment.end_time
                }, {
                    transaction: t
                }
                );

                const enrollments = await Enrollment.findAll({
                    where: {
                        course_id: assignment.course_id
                    },
                    transaction: t
                })

                const user_ids = enrollments.map(enrollment => enrollment.user_id);

                console.log(user_ids);

                for (let user_id of user_ids) {
                    const newUser = await User.findOne({
                        where: {
                            user_id: user_id
                        }
                    })
                    await User_Assignments.create({
                        user_id: user_id,
                        assignment_id: newAssignment.assignment_id,
                        email: newUser.email,
                        title: newAssignment.title
                    }, {
                        transaction: t
                    })
                }

                await t.commit();
                return newAssignment;
            } catch (error) {
                console.error("Error creating user:", error);
                await t.rollback();
                throw error;
            }
        },

        checkPassword: async (parent, args) => {
            const findUser = await User.findOne({ where: { email: args.email } })
            // throw new Error(findUser)
            if (!findUser) {
                throw new Error("Something is wrong")
            }
            const authenticate = await bcrypt.compare(args.password, findUser.password)
            if (authenticate) {
                console.log("Password is Right");
                return findUser
            } else {
                throw new Error("Password is Wrong")
            }
        },
        checkAssignment: async (parent, args) => {
            const today = new Date();
            const year = today.getFullYear();
            const month = today.getMonth() + 1; 
            const day = today.getDate();

            const dateOnlyString = `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year.toString().padStart(2, '0')}`;

            const Assignments = await Assignment.findAll({
                where: {
                    due_date: dateOnlyString,
                    completed: false,
                }
            })

            console.log(Assignments)
            const now = new Date();

            const hours = now.getHours();
            const minutes = now.getMinutes();
            const seconds = now.getSeconds();

            const timeString = `${hours}:${minutes}:${seconds}`;

            // If time ended completed = True 
            await Promise.all(Assignments.map(async (e) => {
                if (e.dataValues.end_time === timeString) {
                    e.completed = true;
                    await e.save();
                    return e;
                }
            }))

            return Assignments;
        },

        addEnrollment: async (parent, { userId, courseId }) => {

            const findEnrollment = await Enrollment.findOne({
                where: {
                    user_id: userId
                }
            })

            if (findEnrollment) {
                throw new Error("Enrollment already done");
            }

            const newEnrollment = await Enrollment.create({
                user_id: userId,
                course_id: courseId
            })
            return newEnrollment;
        },
        checkUserAssignment: async (parent, args) => {
            
            const today = new Date();
            const year = today.getFullYear();
            const month = today.getMonth() + 1; // JavaScript months are 0-indexed
            const day = today.getDate();
            const hours = today.getHours();
            const minutes = today.getMinutes();
            const seconds = today.getSeconds();

            const timeString = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            const dateOnlyString = `${day}/${month.toString().padStart(2, '0')}/${year.toString().padStart(2, '0')}`;

            const currentTimePlusOneHour = new Date(today.getTime() + 60 * 60 * 1000); // Add one hour
            const newHours = currentTimePlusOneHour.getHours().toString().padStart(2, '0');
            const newMinutes = currentTimePlusOneHour.getMinutes().toString().padStart(2, '0');
            const newSeconds = currentTimePlusOneHour.getSeconds().toString().padStart(2, '0');
            const newEndTime = `${newHours}:${newMinutes}:${newSeconds}`;

            console.log(`Current time: ${timeString}`);
            console.log(`Current date: ${dateOnlyString}`);
            console.log(`New end time (current time + 1 hour): ${newEndTime}`);

            console.log(timeString, newEndTime)

            const assignments = await Assignment.findAll({
                where: {
                    due_date: dateOnlyString,
                    completed: false,
                    end_time: newEndTime
                }
            });

            console.log('Assignments:', assignments);

            const assi_ids = assignments.map(e => { return e.assignment_id});

            console.log('Assignment IDs:', assi_ids);

            const users = await User_Assignments.findAll({
                where:{
                    assignment_id: assi_ids,
                    completed: false 
                }
            })

            const user_ids = users.map((e) => {
                return e.user_id;
            })

            console.log(user_ids); 
            
            users.forEach(async (user) => { 
                try {
                    await sendMail(user.email, user.title);
                    console.log(`Email sent to ${user.email}`);
                } catch (error) {
                    console.error(`Error sending email to ${user.email}:`, error);
                }
            })

            return User_Assignments;
        }
    }
}

export default resolvers; 