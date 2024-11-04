import { Sequelize, DataTypes } from "sequelize";

const sequelize = new Sequelize('postgres://devraj:123@localhost:5432/studentmonitoring');

const User = sequelize.define(
    'User', { 
        user_id: { 
            // type: DataTypes.UUID, 
            // defaultValue: DataTypes.UUIDV4,
            type: DataTypes.INTEGER, 
            primaryKey: true,
            autoIncrement: true
        },
        username: {
            type: DataTypes.STRING, 
            allowNull: false ,
            unique: true
        },
        password: {
            type: DataTypes.STRING, 
            allowNull: false 
        },
        email: {
            type: DataTypes.STRING, 
            allowNull: false ,
            unique: true 
        }, 
        role: {
            type: DataTypes.STRING, 
            allowNull: false 
        }
    },
    {
        tableName: 'users',
    }
)

await sequelize.sync({ alter: true })

export default User; 