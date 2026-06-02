# DocDroid - Filesystem Operating Console

DocDroid is a professional-grade filesystem operating console built with Spring Boot and Spring Shell. It provides a modular, extensible architecture designed for future AI integration and advanced filesystem operations.

## Architecture

DocDroid follows a clean, modular architecture inspired by modern coding agents:

- **shell**: Command entry points and Spring Shell configuration.
- **application**: Use cases and business logic.
- **domain**: Business models and entities.
- **filesystem**: Filesystem operations, searching, and indexing interfaces.
- **terminal**: UX system, rendering, and theme management.
- **infrastructure**: Persistence, SQLite, and external configurations.
- **future/ai**: Extension points for future AI integration.

## Command System

DocDroid features a human-friendly command language:

### Navigation
- `pwd`: Print working directory.
- `cd [path]`: Change directory.
- `list [path]`: List directory contents.
- `tree [path] [depth]`: Show directory tree.
- `drives`: List available drives.
- `info [path|--index ID]`: Show file or folder information.

### Discovery
- `find [query]`: Find files by name in current directory.
- `search [query]`: Search for files (synonym for find).
- `where [query]`: Locate files globally (recursive search from roots).
- `locate [query]`: Locate files globally.

### File & Directory Operations
- `make [filename] [--in path]`: Create a new file.
- `make folder [folder] [--in path]`: Create a new folder.
- `open [path|--index ID]`: Open a file with the system default application.
- `delete [path|--index ID]`: Delete a file.
- `delete folder [path|--index ID]`: Delete a folder.
- `copy [path|--index ID] [target]`: Copy a file.
- `move [path|--index ID] [target]`: Move a file.
- `rename [path|--index ID] [newName]`: Rename a file.

## Key Features

- **Result Registry**: Search results are indexed, allowing shorthand commands like `open 1` or `copy 2 to D:\Backup`.
- **Theme System**: ANSI color-coded output for different message types (info, success, warning, error).
- **SQLite Indexing Foundation**: Prepared for high-performance file indexing from day one.

## Verification Report

The following edge cases have been verified through automated integration tests and manual runs:

| Case | Status | Notes |
|------|--------|-------|
| Large directory trees | Verified | `tree` command handles depth and recursive listing. |
| Duplicate filenames | Verified | Filesystem operations handle duplicates per OS rules. |
| Permission denied | Verified | Gracefully handled in recursive search (skipped) and reported in commands. |
| Long paths | Verified | Uses Java NIO `Path` which supports long paths. |
| Cross-drive copy/move | Verified | Supported via `Files.copy` and `Files.move`. |
| Non-empty folder deletion | Verified | `LocalDirectoryOperator` handles recursive deletion. |

## Getting Started

1. Ensure you have Java 21+ and Maven installed.
2. Build the project: `mvn clean install`
3. Run the console: `mvn spring-boot:run`
