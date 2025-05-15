import re


def remove_special_characters(text, allow_spaces=True):
    """
    Removes special characters from a text string, optionally allowing spaces.
    """
    if allow_spaces:
        # Allow spaces in the regex pattern
        pattern = "[^a-zA-Z0-9 ]"
    else:
        # Exclude spaces from the text
        pattern = "[^a-zA-Z0-9]"
    return re.sub(pattern, "", text)


def to_camel_case(text, delimiter=""):
    """
    Converts a given string to camelCase and joins the converted words with a
    specified delimiter.
    If the delimiter is an empty string, the result is a true camelCase.
    """
    words = text.split()
    if delimiter == "":
        # Join the first word in lowercase with the rest of the words capitalized
        return words[0].lower() + "".join(word.capitalize() for word in words[1:])
    else:
        # Join all words with the first word in lowercase and the rest capitalized, separated by the delimiter
        return delimiter.join(
            [words[0].lower()] + [word.capitalize() for word in words[1:]]
        )


def capitalize_each_word(text, delimiter=""):
    """
    Capitalizes the first letter of each word in the string, making all other
    letters lowercase, and joins the words using a specified delimiter.
    """
    capitalized_words = [word.capitalize() for word in text.split()]
    return delimiter.join(capitalized_words)


def capitalize_first_letter(text, delimiter=""):
    """
    Capitalizes only the first letter of the entire string, making all other
    letters lowercase, and joins the result using the specified delimiter.
    """
    result = text[0].upper() + text[1:].lower() if text else ""
    return delimiter.join(result.split())
