class DataWarehouse:
    SessionID = 0
    Date = ""
    Time = ""
    CountPerSession = 0
    CountPerLaps = []
    SessionName = ""
                   
    def __init__(self, SessionID, Date, Time, CountPerSession, CountPerLaps, SessionName) -> None:
        self.SessionID = SessionID
        self.Date = Date
        self.Time = Time
        self.CountPerSession = CountPerSession
        self.CountPerLaps = CountPerLaps
        self.SessionName = SessionName
    
dataWarehouses = []