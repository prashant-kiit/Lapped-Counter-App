import json
import subprocess
from DataWarehouse import DataWarehouse, dataWarehouses

if __name__ == "__main__":
    command = "java QueryClientPython.java 1"

    resultObj = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    results = json.loads(resultObj.stdout)

    print("Results ", results)

    for result in results:  
        dataWarehouse = DataWarehouse(result["SessionID"], result["Date"], result["Time"], result["CountPerSession"], result["CountPerLaps"], result["SessionName"])
        dataWarehouses.append(dataWarehouse)

    print("Error:", resultObj.stderr)
    print("Return Code:", resultObj.returncode) 
    