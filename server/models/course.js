import { Sequelize, DataTypes } from "sequelize";
import User from "./user.js";
import { AllowNull } from "@sequelize/core/decorators-legacy";

const sequelize = new Sequelize('postgres://devraj:123@localhost:5433/studentmonitoring');

const Course = sequelize.define(
    'Course', {
    course_id: {
        // type: DataTypes.UUID, 
        // defaultValue: DataTypes.UUIDV4,
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    user_id: {
        type: DataTypes.INTEGER,
        references: {
            model: User,
            key: 'user_id'
        }
    },
    title: {
        type: DataTypes.STRING(20),
        allowNull: false
    },
    description: {
        type: DataTypes.STRING(100),
        allowNull: false
    },
    start_date: {
        type: DataTypes.STRING(15),
        allowNull: false
    },
    end_date: {
        type: DataTypes.STRING(15),
        allowNull: false
    }
},
    {
        tableName: 'courses',
    }
)

Course.hasMany(User, { foreignKey: 'user_id' })

await sequelize.sync({ alter: true })

export default Course; 