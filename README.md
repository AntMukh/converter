# converter

Converter application.

apk file
https://drive.google.com/file/d/1xFhhXfK7Vv5pICEE5pVmxg23m8wl5m83/view?usp=sharing

The application is designed using MVP 
approach. It has 3 view objects: MainConverterActivity,
ConverterFragment and CurrencyListFragment with corresponding presenters.  Communication interfaces between view and presenters
are defined in Contract interfaces. 

Data layer:
initial data fetch is performed using given API with retrofit 
client and gson converter. After successful fetch data are cashed in preferences and in memory. 
When the application needs to access data,  it first tries to get it from
memory cash, if its empty it tries to get it from preferences,
if data in preferences are absent or outdated, application fetches data
from network. After successful data fetch application can be functional 
even in offline mode

So in the data layer, we have localSource class, which manages Android sharedPreferences and
networkInteractor, which manages Network interactions and Rates repo, which decides
where to get the data from. 

The application uses dependency injection principle. Here it is realized by
injection via constructors without any external tool, like dagger. class dependency
graph can be seen in ConverterApplication class, where we perform all initialisations.

API key and URL are configurable in Gradle script, so we can easily manage 
different versions(ex with a free key or commercial api access)

Local Unit test are to perform network requests, conversion procedure,
for local data repository test, there is an instrumental test, which must be run on a device.

