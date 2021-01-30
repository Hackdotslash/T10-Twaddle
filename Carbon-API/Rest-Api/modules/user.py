"""
| Created: 2017-08-13
| Updated: 2017-08-13
"""
from db import db


class UserModel(db.Model):
    """User model."""

    __tablename__ = 'users'

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80))
    password = db.Column(db.String(80))

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

    @classmethod
    def find_by_username(cls, mail):
        """
        Selects a user from the DB and returns it.

        :param username: the username of the user.
        :type username: str
        :return: a user.
        :rtype: UserModel.
        """
        return cls.query.filter_by(mail=mail).first()

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
    