from ..app import db
from ..utils.distance import pts2dis

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

    def json(self):
        """
        Converts this store and all its items to JSON.

        :return: this store and all its items.
        :rtype: JSON.
        """
        return {'name': self.name,
                'items': [item.json() for item in self.items.all()]}

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
        connected = 0
        if ( distance(self.location_latitude, self.location_longitude , RideShare.location_latitude , RideShare.location_longitude )*1000 < 10.0 ) :
            return 1
        else:
            return 0
            