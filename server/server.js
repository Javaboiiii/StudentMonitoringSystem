import { ApolloServer } from '@apollo/server';
import { startStandaloneServer } from '@apollo/server/standalone';
import { Sequelize } from 'sequelize';
import typeDefs from './typeDefs/typeDefs.js';
import resolvers from './resolvers/resolvers.js';

// const sequelize = new Sequelize('sqlite::memory:') 
const sequelize = new Sequelize('postgres://devraj:123@localhost:5433/studentmonitoring')  
// const sequelize = new Sequelize('postgres://user:pass@example.com:5432/dbname')

const connection = async () => {
    return await sequelize.authenticate(); 
}

try{ 
    connection();
    console.log('Connection established')
}catch(e){
    console.log(e);
}

const server = new ApolloServer({
    typeDefs,
    resolvers,
});

const { url } = await startStandaloneServer(server, {
    listen: { port: 4000 },
});

 
console.log(`🚀  Server ready at: ${url}`);
