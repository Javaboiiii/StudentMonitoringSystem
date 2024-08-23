import { Sequelize, DataTypes } from "sequelize";
import Course from "./course.js";

const sequelize = new Sequelize('postgres://devraj:123@localhost:5433/studentmonitoring');

const Assignmet = sequelize.define(
    'Assignment', { 
        assignment_id: { 
            // type: DataTypes.UUID, 
            // defaultValue: DataTypes.UUIDV4,
            type: DataTypes.INTEGER, 
            primaryKey: true,
            autoIncrement: true
        },
        course_id: {
            type: DataTypes.INTEGER, 
            references: {
                model: Course, 
                key: 'course_id'
            }
        },
        title: {
            type: DataTypes.STRING(40), 
            allowNull: false 
        },
        description: {
            type: DataTypes.STRING(100), 
            allowNull: false 
        }, 
        due_date: {
            type: DataTypes.STRING(15), 
            allowNull: false 
        }
    },
    {
        tableName: 'assignments',
    }
)

Course.hasMany(Assignmet, {foreignKey: 'course_id'})

await sequelize.sync({ alter: true })

export default Assignmet; 