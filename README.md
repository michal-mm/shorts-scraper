** WORK IN PROGRESS
TODO - prepare README file

# Shorts Scraper

A simple Java application that allows you to get a list of all YouTube Shorts videos published by a given channel.

## Description

Shorts Scraper is a command-line tool that uses the YouTube Data API v3 to retrieve and export information about YouTube Shorts from specified channels. The application provides flexible output formatting options to suit different use cases, from data analysis to content management.

## Features

- 🎬 Scrape all YouTube Shorts from any channel
- 🔑 Uses YouTube Data API v3 for reliable data retrieval
- 📊 Multiple output format options (simple txt, CSV, markdown or any implementation that you will provide and configure in the `app.properties` config file)
- 🚀 Simple and lightweight

## Prerequisites

Before you begin, ensure you have the following:

- **Java Development Kit (JDK)** - Version 25 or higher
- **Maven** - For building the project
- **Google API Key** - YouTube Data API v3 access (see setup instructions below)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/michal-mm/shorts-scraper.git
cd shorts-scraper
```

### 2. Build the Project

**Using Maven:**
```bash
mvn clean package
```

### 3. Copy `Shorts-Scraper-App` folder into your preferred destination
```bash
cp -rf target/Shorts-Scraper-App <your_preffered_location>
```

### 4. Prepare `app.properties` configuration file
```bash
cd <your_preffered_location>/Shorts-Scraper-App
cp example.app.properties app.properties
```
Next step is to edit the `app.properties` file. You will have to provide Google API Key (to use YouTubeData API). You also can configure what the preferred output format will be (currently you can select simple txt, CSV or markdown). 

### Required Properties

| Property          | Description                  | Example                      |
|-------------------|------------------------------|------------------------------|
| `youtube.api.key` | Your YouTube Data API v3 key | `AIzaSyB1a2c3d4e5f6g7h8i9j0` |

### Optional Properties

| Property           | Description                | Default                                                                                       |
|--------------------|----------------------------|-----------------------------------------------------------------------------------------------|
| `output.formatter` | Preffered output formatter | `SIMPLE`, <br/>`CSV`, <br/>`MARKDOWN` <br/> <br/> If nothing provided `SIMPLE` is the default |

## Usage

### Running the Application
**Using `Shorts-Scrapper-App` (after building):**
```bash
./shorts-scraper.sh CHANNEL_ID | CHANNEL_NAME
```
| Parameter    | Description | Example |
|--------------|-------------|---------|
| `CHANNEL_ID` | YouTube channel ID | `UC_x5XG1OV2P6uZZ5FSM9Ttw` |
| `CHANNEL_NAME` | YouTube channel name with teh `@` sign | `@java` |


### Example Output

The application will print the output to STDOUT (uses java `IO.println` internally). You can redirect the output to a file if you want:
```bash
./shorts-scraper.sh @java > all-java-shorts.txt
```

## Output Formats

The application supports multiple output formats to suit different needs (you can select the output type in the `app.properties` file):

### SIMPLE - Simple plain TXT format 
```
TODO - simple TXT format
```

### CSV Format
```csv
TODO - CSV format
```


### MARKDOWN Format
```md
todo - MD format
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Disclaimer

This tool is for educational and research purposes only. Please respect YouTube's Terms of Service and API usage policies. Ensure you have permission to scrape data from channels and always comply with data protection regulations.

## Acknowledgments

- YouTube Data API v3 by Google
- All contributors to this project

---

Made by [michal-mm](https://github.com/michal-mm)