# DocumentGenerationPythonLambda

## Prerequisites
* Python 3.6 or higher.


## Managing Project Dependencies

To ensure all dependencies are correctly managed and consistent across different development environments, this project utilizes a `requirements.txt` file. Below are instructions on how to generate and update this file.

It's recommended to use a virtual environment to manage dependencies for your project. This keeps your global site-packages directory clean and manages dependencies on a per-project basis.

1. **Virtual Environment**:
- To create a virtual environment, run the following command in the project directory:
```
python3 -m venv venv
```

- Activating the Virtual Environment:
```
source venv/bin/activate
```

- Deactivate
```
deactivate
```

- Check the current venv
```
python -c "import sys; print(sys.prefix)"
```

2. **Installing and updating Dependencies**: 
- With the virtual environment activated, install the project dependencies with:
```
pip install -r requirements.txt
```
- With the virtual environment activated, update the project dependencies with:
```
python -m pip install --upgrade -r requirements.txt
```
```
python -m pip install --upgrade -r requirements-dev.txt
```

3. **Updating Dependencies**:
- After you install/uninstall any package, Use the `pip freeze` to overwrite the existing `requirements.txt` file with a complete list of all installed Python packages and their versions.
```
pip freeze > requirements.txt
```

## Test handler
```
python -m tests.test_handler
```

## Running Tests
To run tests, use the following command:
```
pytest
```
Test with log
```
pytest -s
```

## Code Style Check 
```
black . 
```

## Check a particular package version
```
python -c "import reportlab; print(reportlab.Version)"
```

## Database check task ID
```
select * from QuickImmiAurora.form_generation_task_table where id=123;
# Press run and see the current database tables below
```


## For local testing, need to make the following change
Lambda load img
"/var/task/resources/logos/concordsage.png"
Local load img
"resources/logos/concordsage.png"

