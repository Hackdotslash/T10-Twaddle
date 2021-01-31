from flask import Flask
from flask_jwt import JWT
from flask_restful import Api
from flask_sqlalchemy import SQLAlchemy



from resources.carbon_footprint import CarbonFootprint
from resources.user import UserRegister
from resources.bluetooth import ConnectivityRegister
from resources.carpool import CarPool

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///data.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.secret_key = 'secret'

from resources.user import UserRegister

api = Api(app)

api.add_resource(CarbonFootprint, '/footprint/<int:maf>')
api.add_resource(UserRegister, '/user_register')
api.add_resource(ConnectivityRegister, '/connectivity')
api.add_resource(CarPool, '/carpool')


if __name__ == '__main__':
    # from db import db
    from resources.user import db
    db.init_app(app)
    app.run(host='172.55.27.167', port=5000, debug=False)
