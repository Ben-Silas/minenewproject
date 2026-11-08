# Silas Ogbeide — Java Portfolio Website

A responsive personal portfolio website served by a Java 17 backend using the JDK's built-in HTTP server. No external libraries or build tools are required.

## Requirements
- Java JDK 17 or newer

## Run on Windows
1. Extract this ZIP file.
2. Open the extracted `silas_java_portfolio` folder in VS Code.
3. Open the terminal in that folder.
4. Compile:

   ```powershell
   javac -d out src/main/java/com/silas/portfolio/Main.java
   ```

5. Run:

   ```powershell
   java -cp out com.silas.portfolio.Main
   ```

6. Open http://localhost:8080 in your browser.

Stop the server with `Ctrl + C`.

## Features
- Responsive portfolio layout with mobile navigation
- About, skills, projects, and contact sections
- Working WhatsApp contact link
- Social profile links based on the handles supplied
- Java backend serves the website and handles a simple health endpoint at `/api/health`

## Personalization notes
- LinkedIn URL was not supplied, so the LinkedIn item is shown as “URL coming soon” rather than linking to an invented address.
- Facebook was supplied as a display name, not a profile URL; it is displayed as text.
- Project descriptions are portfolio placeholders. Replace them with links and details for your actual projects.
