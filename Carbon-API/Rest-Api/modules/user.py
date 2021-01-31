<<<<<<< HEAD
from config import Base
=======
import sys
from sqlalchemy import SQLAlchemy

>>>>>>> 76a291baa24344cf1b5bc109ed48ae5941300294

db = SQLAlchemy()

class UserModel(db.Model):
    """User model."""

    __tablename__ = 'users'

    username = db.Column(db.String(80), primary_key=True)
    mail = db.Column(db.String(80))
    name = db.Column(db.String(80))
    car_model = db.Column(db.String(80)) python models.py

    age = db.Column(db.Integer)



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
    