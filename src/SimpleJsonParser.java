import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleJsonParser {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java SimpleJsonParser <path-to-json-file>");
      System.exit(1);
    }

    String filePath = args[0];

    try {
      String jsonInput = Files.readString(Paths.get(filePath)).trim();
      if (isValidJson(jsonInput)) {
        System.out.println("Valid JSON.");
        System.exit(0);
      } else {
        System.out.println("Invalid JSON.");
        System.exit(1);
      }
    } catch (IOException e) {
      System.out.println("Error reading input file: " + e.getMessage());
      System.exit(1);
    }
  }

  private static boolean isValidJson(String json) {
    // Start parsing the JSON object
    if (json.startsWith("{") && json.endsWith("}")) {
      return parseObject(json.substring(1, json.length() - 1).trim());
    }
    return false; // Must start with '{' and end with '}'
  }

  private static boolean parseObject(String json) {
    if (json.isEmpty()) {
      return true; // Empty object is valid
    }

    String[] pairs = json.split(","); // Split by commas
    for (String pair : pairs) {
      pair = pair.trim();
      if (!isValidPair(pair)) {
        return false; // If any pair is invalid, return false
      }
    }
    return true; // All pairs are valid
  }

  private static boolean isValidPair(String pair) {
    // Each pair should be in the format "key": value
    String[] keyValue = pair.split(":");
    if (keyValue.length != 2) {
      return false; // Must have exactly one key and one value
    }

    String key = keyValue[0].trim();
    String value = keyValue[1].trim();

    // Check if the key is a valid string
    if (!key.startsWith("\"") || !key.endsWith("\"")) {
      return false;
    }

    // Validate the value (can be string, number, boolean, null, or object)
    return isValidValue(value);
  }

  private static boolean isValidValue(String value) {
    value = value.trim();
    if (value.startsWith("\"") && value.endsWith("\"")) {
      return true; // Valid string
    } else if (value.matches("-?\\d+(\\.\\d+)?")) {
      return true; // Valid number
    } else if ("true".equals(value) || "false".equals(value)) {
      return true; // Valid boolean
    } else if ("null".equals(value)) {
      return true; // Valid null
    } else if (value.startsWith("{") && value.endsWith("}")) {
      return parseObject(value.substring(1, value.length() - 1).trim()); // Recursive check for objects
    } else if (value.startsWith("[") && value.endsWith("]")) {
      return parseArray(value.substring(1, value.length() - 1).trim()); // Check for arrays
    }
    return false; // If none of the above, it's invalid
  }

  private static boolean parseArray(String json) {
    if (json.isEmpty()) {
      return true; // Empty array is valid
    }

    String[] values = json.split(","); // Split by commas
    for (String value : values) {
      value = value.trim();
      if (!isValidValue(value)) {
        return false; // If any value is invalid, return false
      }
    }
    return true; // All values are valid
  }
}
