# Debugging Image Issues

## Quick Checklist

1. **Check if images folder exists:**
   - Look for: `ecommerce/src/main/resources/static/images/rice/` or `ecommerce/src/main/resources/images/rice/`

2. **Check database has image paths:**
   ```sql
   USE ecom;
   SELECT riceID, riceName, riceImageFile FROM riceproduct_data LIMIT 5;
   ```

3. **Check browser console (F12):**
   - Look for 404 errors when trying to load images
   - Check the Network tab to see what URLs are being requested
   - Look for console.log messages showing image URLs

4. **Test image URL directly:**
   - Open browser and go to: `http://localhost:8082/images/rice/basmati.jpg`
   - (Replace with your actual image filename)
   - If you get 404, the image isn't in the right location

5. **Verify backend is running:**
   - Backend should be on port 8082
   - Restart backend after adding images

## Common Issues

### Issue 1: Images showing placeholder
- **Cause**: Database doesn't have imageFile values
- **Fix**: Run SQL to add image paths:
  ```sql
  UPDATE riceproduct_data SET riceImageFile = 'rice/basmati.jpg' WHERE riceName = 'Basmati (Jordan Farms)';
  ```

### Issue 2: 404 errors in browser console
- **Cause**: Images not in correct folder or wrong path
- **Fix**: 
  - Place images in: `ecommerce/src/main/resources/static/images/rice/`
  - Or: `ecommerce/src/main/resources/images/rice/`
  - Restart backend after moving images

### Issue 3: Images in wrong location
- **Where images should be:**
  - Option A: `ecommerce/src/main/resources/static/images/rice/filename.jpg`
  - Option B: `ecommerce/src/main/resources/images/rice/filename.jpg`
- **Database should store:**
  - `rice/filename.jpg` (if in rice subfolder)
  - Or just `filename.jpg` (if directly in images/)

### Issue 4: CORS or backend not serving
- Check if backend is running on port 8082
- Check browser console for CORS errors
- Verify WebConfig.java exists and is correct

