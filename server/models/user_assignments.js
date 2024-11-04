import { Sequelize, DataTypes } from 'sequelize'; 
import User from './user.js';
import Assignment from './assignment.js';

const sequelize = new Sequelize('postgres://devraj:123@localhost:5432/studentmonitoring');

const User_Assignments = sequelize.define(
    'User_Assignments', { 
        user_id: { 
            type: DataTypes.INTEGER, 
            references: {
                model: User, 
                key: 'user_id'
            }
        },
        assignment_id: {
            type: DataTypes.INTEGER, 
            references: { 
                model: Assignment, 
                key: 'assignment_id'
            }
        },
        title:{
            type: DataTypes.STRING(40), 
            allowNull: false
        },
        email:{
            type: DataTypes.STRING, 
            allowNull: false
        },
        completed: {
            type: DataTypes.BOOLEAN, 
            defaultValue: false, 
            allowNull: false
        }
    },
    {
        tableName: 'user_assignments',
    }
)

User_Assignments.hasMany(User, { foreignKey: 'user_id' })
User_Assignments.hasMany(Assignment, { foreignKey: 'assignment_id' })

sequelize.sync({ alter: true })

export default User_Assignments; 