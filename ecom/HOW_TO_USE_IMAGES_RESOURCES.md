# How to Use Images from Resources Folder

## Step 1: Add Image Column to Database

If your database already exists, run this SQL:

```sql
USE ecom;
ALTER TABLE riceproduct_data ADD COLUMN riceImageFile VARCHAR(255) NULL;
```

Or use the script: `ecom/add_image_column.sql`

## Step 2: Place Your Images in Resources Folder

1. Navigate to: `ecommerce/src/main/resources/`
2. Create the folder structure: `static/images/rice/`
3. Place your rice product images in this folder

**Folder Structure:**
```
ecommerce/
└── src/
    └── main/
        └── resources/
            ├── application.yml
            └── static/
                └── images/
                    └── rice/
                        ├── basmati.jpg
                        ├── jasmine.png
                        ├── sinandomeng.jpg
                        └── ... (other rice images)
```

## Step 3: Update Database with Image Filenames

You have two options for storing the image path in the database:

### Option A: Store just the filename (Recommended)
Store just the filename in the `riceImageFile` column:
```sql
UPDATE riceproduct_data 
SET riceImageFile = 'rice/basmati.jpg' 
WHERE riceName = 'Basmati (Jordan Farms)';
```

### Option B: Store the full path
Store the full path from images folder:
```sql
UPDATE riceproduct_data 
SET riceImageFile = 'rice/basmati.jpg' 
WHERE riceName = 'Basmati (Jordan Farms)';
```

The frontend will automatically prepend `/images/` to the filename.

## Step 4: Image URLs

Images will be accessible at:
- `http://localhost:8082/images/rice/basmati.jpg`
- `http://localhost:8082/images/rice/jasmine.png`
- etc.

## Example SQL Updates

```sql
USE ecom;

-- Update Basmati rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/basmati.jpg' 
WHERE riceName = 'Basmati (Jordan Farms)';

-- Update Jasmine rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/jasmine.png' 
WHERE riceName = 'Jasmine (Thai/Local)';

-- Update Sinandomeng rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/sinandomeng.jpg' 
WHERE riceName = 'Sinandomeng (Familia)';

-- Continue for other rice types...
```

## Notes

- Spring Boot automatically serves static files from `src/main/resources/static/`
- Images are accessible at `http://localhost:8082/images/{path}`
- No need to restart the server after adding images (just refresh the page)
- Supported formats: JPG, PNG, GIF, WebP

