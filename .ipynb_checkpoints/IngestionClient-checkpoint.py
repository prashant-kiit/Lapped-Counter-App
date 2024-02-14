import json
import subprocess
from DataWarehouse import Document, collection

if __name__ == "__main__":
    command = "java QueryClientPython.java 1"

    resultObj = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    results = json.loads(resultObj.stdout)

    print("Results ", results)
    print(type(results))

    for result in results:  
        print(type(result))
        document = Document(result)
        collection.append(document)

    # print("Error:", resultObj.stderr)
    # print("Return Code:", resultObj.returncode) 

    # print(collection[0].doc_value)
    # print(collection[1].doc_value)
    
    data = []

    for document in collection:
        data.append({'DocumentId': document.doc_value['DocumentId'], 'Result': document.doc_value['Result']})

    print(data)