import json
import subprocess
import traceback
import DataWarehouse as dw

def start(collectionRawDocument):
    command = "java QueryClientPython.java 1"
    resultObj = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    try:
        results = json.loads(resultObj.stdout)
        print("Check ",type(results))
    except Exception as e:
        traceback.print_exc()
    # print("Results ", results)

    for result in results: # type: ignore
        rawDocument = dw.RawDocument(result)
        collectionRawDocument.append(rawDocument)    
    return collectionRawDocument
