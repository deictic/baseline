# baseline
- Basic task variant, without database and API.
- Builds standardly with mvn clean package.
- Type EXIT to exit the app.
- Lombok is used, so standard code (getters, setters, etc) is created automatically at the compile time. 
It does not affect build success or jar functionality, but IDE may be surprised by the absence of methods. Lombok plugin fixes it 
in case it would be necessary to inspect the code through IDE.
