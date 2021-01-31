from flask_restful import Resource, reqparse
from flask_sqlalchemy import SQLAlchemy
import sys

db = SQLAlchemy()


class UserModel(db.Model):
    """User model."""

    __tablename__ = 'users'

    username = db.Column(db.String(80), primary_key=True)
    mail = db.Column(db.String(80))
    name = db.Column(db.String(80))
    car_model = db.Column(db.String(80))
    age = db.Column(db.Integer)
    fuel_type = db.Column(db.Integer)
    year = db.Column(db.Integer)



    def __init__(self, username,mail, car_model, age, year,fuel_type, name):
        """
        username : The uuid of the gmail login
        mail     : mail id of the user
        car_model: The model of the car to be selected from the database of our cars
        age      : age of the user.
        year     : year of the car.
        fuel_type: The fuel type of car : diesel/petrol/CNG
        name     : The name  of the user.  
        """
        self.username = username
        self.mail     = mail
        self.car_model = car_model
        self.age = age
        self.year = year
        self.fuel_type = fuel_type
        self.name = name

    # @classmethod
    # def find_by_username(cls, ):
    #     """
    #     Selects a user from the DB and returns it.

    #     :param username: the username of the user.
    #     :type username: str
    #     :return: a user.
    #     :rtype: UserModel.
    #     """
    #     return cls.query.filter_by(mail=mail).first()

    @classmethod
    def find_by_id(cls, username):
        """
        Selects a user from the DB and returns it.

        :param _id: the id of the user.
        :type _id: int
        :return: a user.
        :rtype: UserModel.
        """
        return cls.query.filter_by(username=username).first()

    def save_to_db(self):
        """
        Inserts this user in the DB.
        """
        db.session.add(self)
        db.session.commit()

    def delete_from_db(self):
        """
        Deletes this user from the DB.
        """
        db.session.delete(self)
        db.session.commit()


 
    
    # create a module for adding the userData model where the details of the user is stored mothefucker!!!
    

class UserRegister(Resource):
    """Users' endpoint."""

    parser = reqparse.RequestParser()
    
    # Parameters for the user login to be posted by the app to the database. 
    parser.add_argument(
        'username',
        type=str,
        required=True,
        help="The the unique uuid of the user!")
    parser.add_argument(
        'mail',
        type=str,
        required=True,
        help="The mail id of the user!")
    parser.add_argument(
        'car_model',
        type=int,
        required=True,
        help="Select the model of your car!"
    )
    parser.add_argument(
        'age',
        type=int,
        help="Enter your age!"
    )
    parser.add_argument(
        'fuel_type',
        type=int,
        help="Select the fuel type of the car!"
    )
    parser.add_argument(
        'year',
        type=int,
        help="Select the year of the model!"
    )

    parser.add_argument(
        'name',
        type=str,
        help="Enter the name of the user!"
    )


    def post(self):
        """
        Creates a new user using the provided username and details.

        :return: success or failure.
        :rtype: application/json response.
        """
        data = UserRegister.parser.parse_args()

        if UserModel.find_by_id(data['username']):
            print("Failed", file=sys.stderr)
            return {
                'message':
                "A user with name '{}' already exists."
                .format(data['username'])
            }, 400


        user = UserModel(**data)  # data['username'], data['details'].......
        user.save_to_db()

        return {"message": "User created successfully."}, 201

    def delete(self):
        """
        Finds a user by its username and deletes it.

        :param username: the username of the user.
        :type username: str
        :return: success or failure.
        :rtype: application/json response.
        """
        data = UserRegister.parser.parse_args()
        user = UserModel.find_by_username(data['username'])

        if user:
            user.delete_from_db()
        else :
            return {'message': 'User not found!'} , 204

        return {'message': 'User deleted'},202
