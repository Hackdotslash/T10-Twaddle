from flask_restful import Resource
from modules.carbon_footprint import get_cabon_footprint

class CarbonFootprint(Resource):
    def get(self, maf):
        return {
            "carbon_footprint" : get_cabon_footprint(maf)
        }
