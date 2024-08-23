const typeDefs = `#graphql 
    type User{ 
        user_id: String!
        username: String!
        password: String! 
        email: String! 
        role: String!  
    }

    type Course { 
        course_id: String! 
        user_id: String!
        title: String! 
        description: String! 
        start_date: String
        end_date: String
        user: User!
    }

    type Assignment { 
        assignment_id: String! 
        title: String! 
        description:String! 
        due_date: String
        course_id: String! 
        course: Course
    }

    type Enrollment { 
        user_id: String! 
        course_id: String! 
        courses: [Course]
        users: [User]
    }

    input Cuser { 
        username: String!
        password: String! 
        email: String! 
        role: String! 
    }
    input Ccourse { 
        title: String! 
        user_id: String!
        description: String! 
        start_date: String
        end_date: String
    }
    input Cassignment { 
        title: String! 
        description:String! 
        due_date: String!
        course_id: String!
    }

    type Query{ 
        users: [User!]
        user(user_id: String!) : User
        assignment: [Assignment]
        courses: [Course!]
        enrollments: [Enrollment]
    }

    type Mutation{ 
        createUser(user: Cuser!): User!
        createCourse(course: Ccourse!): Course!
        createAssignment(assignment: Cassignment!): Assignment!
        checkPassword(password: String!, username: String!): User!
    }
`

export default typeDefs