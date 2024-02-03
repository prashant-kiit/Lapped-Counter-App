import json
import subprocess

command = "java QueryClient.java 1"

resultObj = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

results = json.loads(resultObj.stdout)

print("Results ", results)

for result in results:    
    print("Value of 'CountPerSession':", resultObj["CountPerSession"])
    print("Value of 'CountPerLaps':", resultObj["CountPerLaps"])

print("Error:", result.stderr)
print("Return Code:", result.returncode)
