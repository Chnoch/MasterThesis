__author__ = 'Chnoch'


class Node:
    def __init__(self, user, station_id, station):
        self.user = user
        self.station_id = station_id
        self.station = station

    def __repr__(self):
        return "Node " + self.station

    def __cmp__(self, other):
        return self.user == other.user and self.station_id == other.station_id

