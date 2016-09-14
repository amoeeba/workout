import argparse
import re
import json
import statistics
import numpy as np

parser = argparse.ArgumentParser()
parser.add_argument("-f", "--filename", help="Enter Log file name")
args = parser.parse_args()


def open_log():
    f = open(args.filename, 'r')
    contents = f.read()
    #regex = '([^\s]+) "(.*?)" (\d+) (\d+) (\d+) (\d+)'
    regex = '([^\s]+) "(.*?)" (.*?) (.*?) (.*?) (.*)'
    pattern = re.compile(regex, re.MULTILINE)
    route_length = []
    short_response_time = []
    for match in pattern.finditer(contents):
        if match.group(6) is not '-':
            x = int(match.group(6))/1000
            route_length.append(int(x))
            if int(x) < 100:
                short_response_time.append(int(match.group(4)))
    route_length_full = []
    for match2 in pattern.finditer(contents):
        route_length_full.append(match2.group(6))
    short = []
    long = []
    v_long = []
    other = []
    for j in route_length_full:
        if j == '-':
            other.append(j)

    for i in route_length:
        if i < 100:
            short.append(i)
        elif i >= 100 and i < 1000:
            long.append(i)
        elif i >= 1000:
            v_long.append(i)

    max_route_length = max(route_length)
    avg_short_response_time = (sum(short_response_time) / len(short_response_time)) / 1000
    short_std_dev = statistics.stdev(short_response_time) / 1000
    short_per_cal = np.percentile(short_response_time, 98) / 1000

    data = {
        "request_count":
            {
                "shortroute": "%s" % len(short),
                "longroute": "%s" % len(long),
                "verylongroute": "%s" % len(v_long),
                "other": "%s" % len(other)
            },
        "shortroute_statistics":
            {
                "average_response_time": "%s" % avg_short_response_time,
                "standard_deviation_response_time": "%s" % short_std_dev,
                "98th_percentile_response_time": "%s" % short_per_cal
            },
        "max_route_length": "%s" % max_route_length
    }

    with open('result.json', 'w') as f:
        json.dump(data, f, sort_keys=False, indent=4, separators=(',', ':'))

if __name__ == "__main__":
    open_log()
