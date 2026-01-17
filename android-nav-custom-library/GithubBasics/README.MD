### **Step-by-Step Guide**

#### 1. **Navigate to Your Local Folder (if needed)**
   - If you're already in the correct folder (`DSA`), you can skip this step.
   - If not, navigate to your `DSA` folder where the `array` directory is located:
     ```bash
     cd path/to/DSA
     ```

#### 2. **Initialize Git in the Local Folder**
   - Inside the `DSA` folder, initialize Git:
     ```bash
     git init
     ```

#### 3. **Add Your GitHub Remote Repository**
   - Link the local Git repository to your empty GitHub repository (make sure you have the correct URL from your GitHub repository page):
     ```bash
     git remote add origin https://github.com/ImthiyaszAlam/DSA.git
     ```

#### 4. **Stage Your Changes**
   - Since you have a directory `array` with files in it, you can stage all files in your project for the first commit:
     ```bash
     git add .
     ```

#### 5. **Commit Your Changes**
   - Commit the changes with an appropriate message. For example:
     ```bash
     git commit -m "Initial commit with array directory"
     ```

#### 6. **Rename the `master` Branch to `main` (Optional but recommended)**
   - If you want to use `main` as the default branch (recommended), run:
     ```bash
     git branch -M main
     ```

#### 7. **Push to GitHub**
   - Now, push your local commits to your GitHub repository. Since this is the first push, you’ll need to set the upstream branch to `main`:
     ```bash
     git push -u origin main
     ```

#### 8. **Verify on GitHub**
   - Go to your GitHub repository page [https://github.com/ImthiyaszAlam/DSA](https://github.com/ImthiyaszAlam/DSA) and refresh the page.
   - You should now see your `array` directory along with its contents in the repository.

---

### **Daily Workflow for Future Changes**

After the initial setup, here’s how you can push updates every day:

1. **Make changes in your `array` directory**.
2. **Stage your changes**:
   ```bash
   git add .
   ```
3. **Commit your changes**:
   ```bash
   git commit -m "Description of your changes"
   ```
4. **Push to GitHub**:
   ```bash
   git push origin main
   ```
