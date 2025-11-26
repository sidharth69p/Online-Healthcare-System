# Online Healthcare System (v3) - Full Feature Pack

This is version 3 (ZIP #3) and includes:
- DAO + JDBC implementations (User + Appointment)
- Appointment booking UI integrated with service layer
- Multithreading: SwingWorker + ScheduledExecutorService for reminders
- BCrypt password hashing (jBCrypt)
- Simple analytics using JFreeChart
- PDF export using Apache PDFBox
- FlatLaf Light/Dark theme toggle
- Gradle build

## How to run
1. Install JDK 17+ and Gradle or use the Gradle wrapper.
2. Extract project and edit `src/main/resources/config.properties` to set DB credentials.
3. (Optional) Run `sql/schema_full.sql` to create DB schema and sample data.
4. Run:
   ```
   ./gradlew run
   ```
   or on Windows:
   ```
   gradlew.bat run
   ```

## Notes
- No real credentials are committed.
- Demo credentials are in `config.properties` for presentation.
- For real deployment, create users via registration flow or load sample SQL.
