from ..app import db


class NearbyConnections(db.Model):
    """Database model for car pooling feature."""

    __tablename__ = 'carpool'

    id = db.Column(db.Integer, primary_key=True)
    bluetooth_addr = db.Column(db.String(80))
    bluetooth_nearby = db.Column(db.String(80))    

    def __init__(self, id, bluetooth_addr, bluetooth_nearby):
        self.id = self.id           # to give a unique id each time nearby connection
        self.bluetooth_nearby= bluetooth_nearby
        self.bluetooth_addr = bluetooth_addr

    def json(self):
        """
        Converts this item to JSON.

        :return: this item.
        :rtype: JSON.
        """
        return {'id': self.id, 'bluetooth_addr': self.bluetooth_addr, 'bluetooth_nearby':self.bluetooth_nearby}

    @classmethod
    def find_by_id(cls, uuid):
        """
        Selects an item from the DB and returns it.

        :param name: the uuid
        :type name: str
        :return: an item.
        :rtype: NearbyConnections.
        """
        return cls.query.filter_by(uuid).first()

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
    def available_devices(cls ,  host_uuid):
        return {
            'host': host_uuid,
            'devices': [ i for i in cls.query.filter(uuid=host_uuid) ]
        }
    
