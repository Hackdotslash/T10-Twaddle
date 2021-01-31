from flask_jwt import jwt_required
from flask_restful import Resource, reqparse
from resources.bluetooth import Connectivity
from flask_sqlalchemy import SQLAlchemy
from bluetooth import Connectivity


db = SQLAlchemy()

class NearbyConnections(db.Model):
    """Database model for car pooling feature."""

    __tablename__ = 'carpool'

    id = db.Column(db.Integer, primary_key=True)
    bluetooth_addr = db.Column(db.String(80))
    bluetooth_nearby = db.Column(db.String(80))    
    location_latitude = db.Column(db.Float)
    location_longitude = db.Column(db.Float)

    def __init__(self, id, bluetooth_addr, bluetooth_nearby, location_latitude, location_longitude):
        self.id = id           # to give a unique id each time an objec is instantiated.
        self.bluetooth_nearby = bluetooth_nearby
        self.bluetooth_addr = bluetooth_addr
        self.location_latitude = location_latitude
        self.location_longitude = location_longitude

    def json(self):
        """
        Converts this item to JSON.

        :return: this item.
        :rtype: JSON.
        """
        return {'id': self.id, 'bluetooth_addr': self.bluetooth_addr, 'bluetooth_nearby':self.bluetooth_nearby,
                'location_longitude' :self.location_longitude, 'location_latitude':self.location_latitude}

    @classmethod
    def find_by_id(cls, id):
        """
        Selects an item from the DB and returns it.

        :param name: the uuid
        :type name: str
        :return: an item.
        :rtype: NearbyConnections.
        """
        return cls.query.filter_by(id=id).first()
    
    @classmethod
    def find_by_addr(cls, addr):
        """
        Selects an item from the DB and returns it.

        :param name: the uuid
        :type name: str
        :return: an item.
        :rtype: NearbyConnections.
        """
        return cls.query.filter_by(bluetooth_addr=addr)


    def save_to_db(self):
        """
        Inserts this item in the DB.
        """
        db.session.add(self)
        db.session.commit()

    def delete_from_db(self):
        """
        Deletes this item from the DB.
        """
        db.session.delete(self)
        db.session.commit()
    
    """
    Returns a dict containing the bluetooth addrs nearby a bluetooth device.
    """
    @classmethod
    def available_devices(cls , carpooler_addr):
        return {
            'carpooler_addr': carpooler_addr,
            'devices': [ device.bluetoooth_nearby for device in cls.query.filter(bluetooth_addr=bluetooth_addr) ]
        }

    def validate_carpool(self, nearby_addr):
        RideShare = Connectivity.query.filter_by(bluetooth_addr=nearby_addr)
        if ( distance(self.location_latitude, self.location_longitude , RideShare.location_latitude , RideShare.location_longitude )*1000 < 10.0 ) :
            return 1
        else:
            return 0
    


class CarPool(Resource):
    """carpooling' endpoint."""

    parser = reqparse.RequestParser()
    parser.add_argument(
        'id',
        type=str,
        required=True,
        help="This cannot be blank"
    )
    parser.add_argument(
        'uuid',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )
    parser.add_argument(
        'bluetooth_addr',
        type=str,
        required=True,
        help="Bluetooth addr of the user."
    )
    parser.add_argument(
        'bluetoooth_nearby',
        type=str,
        required=True,
        help="nearby bluetooth device"
    )

    # @jwt_required()
    def get(self, uuid):
        """
        Finds validation of carpooling   and returns it.

        :param name: the uuid of the user.
        :type name: str
        :return: item data.
        :rtype: application/json.
        """
        connection = NearbyConnections.find_by_id(id=id)
        devices = connection.available_devices(connection.uuid)

        carpooling = dict()

        carpooling['host_uuid'] = devices['host']
        carpooling['carpool'] = []
        carpool_host = Connectivity.find_by_id(carpooling['host_uuid'])
        for device in devices['devices']:
            if(carpool_host.validate_carpool(device.bluetooth_addr)):
                carpooling['carpool'].append(device)
        
        return carpooling
        # if item:
        #     return item.json()
        # return {'message': 'Item not found'}, 404

    def post(self):
        """
        Creates a databse of user and nearby devices
    
        :param uuid : uuid of the user.
        :type name: str
        :return: success or failure message.
        :rtype: application/json response.
        """
        request_data = CarPool.parser.parse_args()

        data = NearbyConnections(**request_data)
        if NearbyConnections.find_by_id(data.id):
            return {'message': "An user with uuid '{}' already exists."
                               .format(data.uuid)}, 400

        try:
            data.save_to_db()
        except:
            return {'message': 'An error occurred inserting the item.'}, 500

        return data.json(), 201

    def delete(self, uuid):
        """
        Finds an item by its uuid and deletes it.

        :param name: the name of the item.
        :type name: str
        :return: success or failure message.
        :rtype: application/json response.
        """
        request_data = CarPool.parser.parse_args()

        data = NearbyConnections(**request_data)

        item = NearbyConnections.find_by_uuid(uuid)
        if item:
            item.delete_from_db()
        return {'message': 'Item deleted'}

    def put(self, bluetooth_addr):
        """
        Creates or updates an item using the provided bluetooth_addr.

        :param name: the name of the item.
        :type name: str
        :param price: the item's price.
        :type items: float
        :param store_id: the item's parent store.
        :type store_id: int
        :return: success or failure message.
        :rtype: application/json response.
        """
        request_data = CarPool.parser.parse_args()
        # data = NearbyConnections.fin
        data = NearbyConnections.find_by_addr(bluetooth_addr)

        if data is None:
            data = NearbyConnections(**request_data)
        else:
            data.location_latitude = request_data['location_latitude']
            data.location_longitude = request_data['location_longitude']
        data.save_to_db()
        return data.json()


# class ItemList(Resource):
#     """Stores' list endpoint."""

#     @classmethod
#     def get(cls):
#         """
#         Returns a list of all items.

#         :return: all stores' data.
#         :rtype: application/json.
#         """
#         return {'items': [item.json() for item in ItemModel.query.all()]}
