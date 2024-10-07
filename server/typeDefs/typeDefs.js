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
        end_time: String!
        completed: Boolean!
        course: Course
    }

    type Enrollment { 
        user_id: String! 
        course_id: String! 
        course: Course
        user: User
    }

    type User_Assignments { 
        user_id: String!
        assignment_id: String! 
        title: String!
        email: String!
        completed: Boolean
        users: [User]
        assignment: [Assignment]
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
        end_time: String
    }
    input Cassignment { 
        title: String! 
        description:String! 
        due_date: String!
        course_id: String!
        end_time: String!
    }

    type Query{ 
        users: [User!]
        user(user_id: String!) : User
        assignment: [Assignment]
        courses: [Course!]
        enrollments: [Enrollment]
        user_assignments: [User_Assignments]
    }

    type Mutation{ 
        createUser(user: Cuser!): User!
        createCourse(course: Ccourse!): Course!
        createAssignment(assignment: Cassignment!): Assignment!
        checkPassword(password: String!, username: String!): User!
        checkAssignment: [Assignment]!
        addEnrollment(userId: String!, courseId: String): Enrollment
        checkUserAssignment: User_Assignments
    }
`

export default typeDefs