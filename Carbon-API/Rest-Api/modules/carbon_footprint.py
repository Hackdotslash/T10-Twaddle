def get_cabon_footprint(maf):
    fuel_consumed = (maf / (14.7 * 6.17 * 454)) * 3.78541 # litres 
    carbon_footprint =  fuel_consumed * 2.3 # kg per minute
    print(fuel_consumed, carbon_footprint)
    return carbon_footprint


if __name__ == "__main__":
    get_cabon_footprint(600)
