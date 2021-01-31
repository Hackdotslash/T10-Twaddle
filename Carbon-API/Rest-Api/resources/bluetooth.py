from flask_restful import Resource, reqparse
from flask_sqlalchemy import SQLAlchemy
import sys


db = SQLAlchemy()

class Connectivity(db.Model):
    """Connectivity model."""

    __tablename__ = 'connectivity'

    uuid = db.Column(db.String(80), primary_key=True)
    username = db.Column(db.String(80))
    bluetooth_name = db.Column(db.String(80))
    bluetooth_addr  = db.Column(db.String(80))
    location_latitude = db.Column(db.Float)
    location_longitude = db.Column(db.Float)



    def __init__(self, uuid, username, bluetooth_name,
                bluetooth_addr, location_latitude, location_longitude):
    
        self.uuid = uuid
        self.bluetooth_name= bluetooth_name
        self.bluetooth_addr = bluetooth_addr
        self.username = username
        self.location_latitude = location_latitude
        self.location_longitude = location_longitude

    @classmethod
    def json(self):
        """
        Converts this returns its items to JSON.

        :return: this store and all its items.
        :rtype: JSON.
        """
        return {'name': self.uuid,
                'details': [details.json() for details in cls.query.filter_by(uuid=self.uuid)]}

    @classmethod
    def find_by_uuid(cls, uuid):
        """
        Selects a store from the DB and returns it.

        :param name: the name of the store.
        :type name: str
        :return: a store.
        :rtype: StoreModel.
        """
        return cls.query.filter_by(uuid=uuid).first()

    def save_to_db(self):
        """
        Inserts this store in the DB.
        """
        db.session.add(self)
        db.session.commit()

    def delete_from_db(self):
        """
        Deletes this store from the DB.
        """
        db.session.delete(self)
        db.session.commit()

    def validate_carpool(self, nearby_addr):
        RideShare = cls.query.filter_by(bluetooth_addr=nearby_addr)
        # connected = 0
        if ( distance(self.location_latitude, self.location_longitude , RideShare.location_latitude , RideShare.location_longitude )*1000 < 10.0 ) :
            return 1
        else:
            return 0
    


class ConnectivityRegister(Resource):
    """Connectivity endpoints."""

    parser = reqparse.RequestParser()
    parser.add_argument(
        'uuid',
        type=str, 
        required=True,
        help="The Unique UUId of the user"
    )
    parser.add_argument(
        'bluetooth_name',
        type=str
    )
    parser.add_argument(
        'bluetooth_addr',
        type=str,
        required=True,
        help="The bluetooth address of the user!"
    )

    parser.add_argument(
        'username',
        type=str,
        help="the username of the user."
    )
    parser.add_argument(
        'location_latitude',
        type=float,
        required=True,
        help="Location latitude of the user."
    )
    parser.add_argument(
        'location_longitude',
        type=float,
        required=True,
        help="Location longitude of the user."
    )
    def get(self, uuid):
        """
        Finds a bluetooth by its uuid and returns it.

        :param name: the uuid of the user.
        :type name: str
        :return: user data.
        :rtype: application/json.
        """
        connection = Connectivity.find_by_uuid(uuid)
        if connection:
            return connection.json()
        return {'message': 'User not found'}, 404

    def post(self):
        """
        Creates a new user using the provided addr and list of details.



        :param name: the uuid of the user.
        :type name: str
        :param items: the users device details.
        :type items: str list
        :return: success or failure message.
        :rtype: application/json response.
        """
        data = ConnectivityRegister.parser.parse_args()

        connection = Connectivity(**data)
        if Connectivity.find_by_uuid(connection.uuid)::
            return {
                'message': "A user with uuid '{}' already exists."
                        .format(connection.uuid)
            }, 400


        try:
            connection.save_to_db()
        except Exception as err:
            print(connection, err, file=sys.stderr)
            return {'message': 'An error occurred inserting the user.'}, 500

        return {"details" : "Connected"}, 201

    def delete(self):
        """
        Finds a user by its uuid and deletes it.

        :param name: unique uuid of the user.
        :type name: str
        :return: success or failure message.
        :rtype: application/json response.
        """
        request_data = ConnectivityRegister.parser.parse_args()

        data = Connectivity(**request_data)
        
        connection = Connectivity.find_by_uuid(data.uuid)
        if connection:
            connection.delete_from_db()
        return {'message': 'Store deleted'}


# class ConnectivityList(Resource):
#     """Stores' list endpoint."""

#     @classmethod
#     def get(cls):
#         """
#         Returns a list of all stores.

#         :return: all stores' data.
#         :rtype: application/json.
#         """
#         return {'stores': [store.json() for store in StoreModel.query.all()]}

