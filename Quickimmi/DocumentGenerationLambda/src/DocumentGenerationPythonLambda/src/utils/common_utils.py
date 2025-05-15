import ast

def flatten_dict(d, parent_key="", sep="."):
    """
    Flattens a nested dictionary, using a separator to denote levels of hierarchy.

    Args:
    - d (dict): The dictionary to flatten.
    - parent_key (str): The base key string for the recursion level.
    - sep (str): The separator between keys in the flattened dictionary.

    Returns:
    - dict: A new dictionary with flattened keys.
    """
    items = []
    for k, v in d.items():
        new_key = f"{parent_key}{sep}{k}" if parent_key else k
        if isinstance(v, dict):
            items.extend(flatten_dict(v, new_key, sep).items())
        else:
            items.append((new_key, v))
    return dict(items)


def convert_string_to_list(value):
    try:
        # Attempt to convert the string to a list using ast.literal_eval()
        return ast.literal_eval(value)
    except (SyntaxError, ValueError):
        # If ast.literal_eval() fails, return the original string
        return value
