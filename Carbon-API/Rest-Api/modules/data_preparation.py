import csv
import datetime
import pandas as pd


def get_data(data = []):
    for data_file in ["data1.csv"]:#["data1.csv", "data2.csv"]:
        with open('../data/obd_datasets/{}'.format(data_file), mode='r') as csv_file:
            csv_reader = csv.DictReader(csv_file)
            line_count = 0
            skipped_rows = 0
            for row in csv_reader:
                try:
                    int(row["MAF"].split(",")[1].split("g")[0]), int(row["ENGINE_LOAD"].split(",")[0]), int(row["ENGINE_RPM"].split("R")[0]), int(row["SPEED"].split("k")[0]), datetime.datetime.strptime(row["ENGINE_RUNTIME"], '%H:%M:%S').time()
                    if row["MAF"] != "" and row["ENGINE_LOAD"] != "" and row["ENGINE_RPM"] != "" and row["SPEED"] != "" and row["ENGINE_RUNTIME"] != "":
                        data.append({
                            "FUEL_LEVEL" : int(row["MAF"].split(",")[1].split("g")[0]),
                            "ENGINE_LOAD" : int(row["ENGINE_LOAD"].split(",")[0]),
                            "ENGINE_RPM" : int(row["ENGINE_RPM"].split("R")[0]),
                            "SPEED" : int(row["SPEED"].split("k")[0]),
                            "ENGINE_RUNTIME" : datetime.datetime.strptime(row["ENGINE_RUNTIME"], '%H:%M:%S').time()
                        })
                    else: skipped_rows += 1
                except: 
                    #print(line_count)
                    skipped_rows += 1
                line_count += 1
    #print(data[0], data[1])
    return data


def time_to_hrs(t):
    return float(t.hour + (t.minute / 60 + t.second / 3600))


def format_data(data):
    formatted_data = []
    temp = []
    for i in range(1, len(data)):
        prev_row = data[i- 1]
        row = data[i]
        time_diff = abs(time_to_hrs(row["ENGINE_RUNTIME"]) - time_to_hrs(prev_row["ENGINE_RUNTIME"]))
        #print(time_diff, type(time_diff))
        try:
            float(abs(row["SPEED"] - prev_row["SPEED"]) / time_diff)
            formatted_data.append({
                "FUEL_CONSUMED" : prev_row["FUEL_LEVEL"],
                "DISTANCE" : float(((row["SPEED"] + prev_row["SPEED"]) / 2) * time_diff),
                "ACCELERATION" : float(abs(row["SPEED"] - prev_row["SPEED"]) / time_diff),
                "ENGINE_LOAD" : row["ENGINE_LOAD"],
                "ENGINE_RPM" : row["ENGINE_RPM"]
            })
        except:
            pass
    for i in range(0, len(formatted_data), 10):
        fuel, acceleration, distance, engine_load, engine_rpm = 0, 0, 0, 0, 0
        for j in range(i, min(i + 11, len(formatted_data))):
            fuel += formatted_data[j]["FUEL_CONSUMED"]
            distance += formatted_data[j]["DISTANCE"]
            acceleration += formatted_data[j]["ACCELERATION"]
            engine_load += formatted_data[j]["ENGINE_LOAD"]
            engine_rpm += formatted_data[j]["ENGINE_RPM"]
        temp.append({
            "FUEL_CONSUMED" : fuel,
            "DISTANCE" : distance,
            "ACCELERATION" : acceleration / 5,
            "ENGINE_LOAD" : engine_load / 5,
            "ENGINE_RPM" : engine_rpm / 5
        })
    return temp



if __name__ == "__main__":
    data = get_data()
    formatted_data = format_data(data)
    df = pd.DataFrame(formatted_data, columns=["FUEL_CONSUMED", "DISTANCE", "ACCELERATION", "ENGINE_LOAD", "ENGINE_RPM"])
    export_train_data_csv = df[:-150].to_csv(r'../data/train/train.csv', index=None, header=True)
    #print(len(df[len(df) - 150:]))
    export_test_data_csv = df[len(df) - 150:].to_csv(r'../data/test/test.csv', index=None, header=True)
    print(df)