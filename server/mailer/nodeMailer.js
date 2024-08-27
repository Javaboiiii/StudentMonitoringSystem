import nodemailer from 'nodemailer';
import Mailgen from 'mailgen';

const sendMail = async (req, res, info) => { 
    let config = { 
        service: 'gmail', 
        auth: { 
            user: 'devrajlokhande1610@gmail.com', 
            pass: 'eientcodhtiwkdyl'
        }
    }

    let transporter = nodemailer.createTransport(config)

    let MailGenerator = new Mailgen({ 
        theme: 'default', 
        product: {
            name: 'Mailgen', 
            link: 'https://mailgen.js/'
        }
    })

    let response = { 
        body: {
            name : "Daily Tuition",
            intro: "Your bill has arrived!",
            table : {
                data : [
                    {
                        item : "Nodemailer Stack Book",
                        description: "A Backend application",
                        price : "$10.99",
                    }
                ]
            },
            outro: "Looking forward to do more business"
        }
    }
}