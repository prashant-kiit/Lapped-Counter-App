import DataWarehouse as dw
import IngestionClient as ic
import Cleaner as cl

if __name__ == "__main__":
    collection = dw.CollectionSingleton.getCollection()
    collection = ic.start(collection)
    status = cl.start(collection)
    print('\n' + status)