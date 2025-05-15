from src.utils.common_utils import convert_string_to_list
import ast


def update_template_body(template, data_fields):
    """
    Updates the body of the cover letter template with specified data fields.

    This function modifies the template dictionary in-place, replacing
    placeholders in the 'body' section with values from the data_fields dict.

    Args:
    - template (dict): The cover letter template. It should have a 'body'
                        key whose value is a dictionary of lists containing
                        strings for formatting.
    - data_fields (dict): Key-value pairs used to replace placeholders in
                        the template.
    Returns:
    - None: The template is modified in-place; no value is returned.
    """
    # Check if 'body' key exists in the template.
    if "body" not in template:
        raise KeyError("Template missing 'body' part")

    try:
        for key, content_list in template["body"].items():
            updated_content = []
            for string in content_list:
                # Replace the placeholder
                updated_string = string.format(**data_fields)
                # Convert the string to a list if it is in list format
                updated_string = convert_string_to_list(updated_string)
                if isinstance(updated_string, list):
                    # If a list, extend the updated_content with the list items
                    updated_content.extend(updated_string)
                else:
                    # Otherwise, just append the result as a single item
                    updated_content.append(updated_string)
            # Replace the original content with the updated content
            template["body"][key] = updated_content

    except ValueError as e:
        # Handle errors from incorrect format strings.
        raise ValueError(f"Error formatting string with data_fields: {str(e)}")

    return template

def update_template_format(template, data_fields):
    """
    Updates the body of the cover letter template with specified data fields.

    This function modifies the template dictionary in-place, replacing
    placeholders in the 'body' section with values from the data_fields dict.

    Args:
    - template (dict): The cover letter template. It should have a 'body'
                        key whose value is a dictionary of lists containing
                        strings for formatting.
    - data_fields (dict): Key-value pairs used to replace placeholders in
                        the template.
    Returns:
    - None: The template is modified in-place; no value is returned.
    """
    # Check if 'body' key exists in the template.
    if "format" not in template:
        raise KeyError("Template missing 'format' part")

    try:
        for key, content_list in template["format"]["body"].items():
            for key, val in content_list.items():
                # Replace the placeholder
                if isinstance(val, list):
                    print("key: %s" % key)
                    print("val: %s" % val)
                    for list1 in val:
                        list1[0] = list1[0].format(**data_fields)
                    print("updated val: %s" % val)


    except ValueError as e:
        # Handle errors from incorrect format strings.
        raise ValueError(f"Error formatting string with data_fields: {str(e)}")

    return template


def update_template_checkbox(checkbox_data, data_fields):
    """
    Updates all checkbox labels in a given structured checkbox data with specified data fields.
    This function also handles the 'underline_phrases' field if it is present, updating any placeholders.

    This function modifies the checkbox_data in-place by replacing placeholders in the labels
    and underline_phrases within each checkbox set with values from the data_fields dict.

    Args:
    - checkbox_data (dict): A dictionary of checkbox sets, each containing a list of checkboxes,
                            where each checkbox is a dictionary with properties like 'label'.
    - data_fields (dict): Key-value pairs used to replace placeholders in the labels and underline_phrases.
    Returns:
    - dict: The updated checkbox data.
    """
    for key, checkboxes in checkbox_data.items():
        for checkbox in checkboxes:
            # Replace placeholders in the label using data fields, handling errors
            try:
                checkbox["label"] = checkbox["label"].format(**data_fields)
                # Also process underline_phrases if they exist
                if "underline_phrases" in checkbox:
                    updated_phrases = []
                    for phrase, style in checkbox["underline_phrases"]:
                        # Replace placeholders within each phrase
                        updated_phrase = phrase.format(**data_fields)
                        updated_phrases.append((updated_phrase, style))
                    checkbox["underline_phrases"] = updated_phrases

            except KeyError as e:
                # Log or handle the error appropriately if a key is missing
                raise KeyError(
                    f"Missing data for key: {str(e)} in label or underline_phrases formatting"
                )
            except ValueError as e:
                # Handle formatting errors
                raise ValueError(
                    f"Error formatting label or underline_phrases: {str(e)}"
                )

    return checkbox_data
