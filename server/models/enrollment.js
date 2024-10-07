import { Sequelize, DataTypes } from "sequelize";
import Course from "./course.js";
import User from "./user.js";

const sequelize = new Sequelize('postgres://devraj:123@localhost:5433/studentmonitoring');

const Enrollment = sequelize.define(
    'Enrollment', { 
        user_id: { 
            type: DataTypes.INTEGER, 
            references: {
                model: User, 
                key: 'user_id'
            }
        },
        course_id: {
            type: DataTypes.INTEGER, 
            references: { 
                model: Course, 
                key: 'course_id'
            }
        }
    },
    {
        tableName: 'enrollments',
    }
)

Enrollment.hasMany(User, { foreignKey: 'user_id' })
Enrollment.hasMany(Course, { foreignKey: 'course_id' })

sequelize.sync({ alter: true })

export default Enrollment; 