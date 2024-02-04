import json
import subprocess

command = "java QueryClientPython.java 1"

resultObj = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

results = json.loads(resultObj.stdout)

print("Results ", results)

for result in results:    
    print("Value of 'CountPerSession':", result["CountPerSession"])
    print("Value of 'CountPerLaps':", result["CountPerLaps"])

print("Error:", resultObj.stderr)
print("Return Code:", resultObj.returncode)
